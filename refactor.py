import os
import re

base_dir = "src/main/java/realestate/server/application/realestate"
test_dir = "src/test/java/realestate/server/application/realestate"

replacements = {
    # Imports
    "import realestate.server.application.realestate.domain.DataFetchPort;": "import realestate.server.application.realestate.domain.DataFetchPort;",
    "import realestate.server.application.realestate.domain.AptPblancRepository;": "import realestate.server.application.realestate.domain.AptPblancRepository;",
    "import realestate.server.application.realestate.domain.RentPblancRepository;": "import realestate.server.application.realestate.domain.RentPblancRepository;",
    "import realestate.server.application.realestate.domain.DealsRepository;": "import realestate.server.application.realestate.domain.DealsRepository;",
    
    "import realestate.server.application.realestate.application.AptPblancService;": "import realestate.server.application.realestate.application.service.AptPblancService;",
    "import realestate.server.application.realestate.application.RentPblancService;": "import realestate.server.application.realestate.application.service.RentPblancService;",
    "import realestate.server.application.realestate.application.DealsService;": "import realestate.server.application.realestate.application.service.DealsService;",
    "import realestate.server.application.realestate.application.RealestateDealsService;": "import realestate.server.application.realestate.application.service.RealestateDealsService;",
}

for root_dir in [base_dir, test_dir]:
    if not os.path.exists(root_dir):
        continue
    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith(".java"):
                file_path = os.path.join(root, file)
                with open(file_path, 'r', encoding='utf-8') as f:
                    content = f.read()

                original_content = content
                
                # Replace imports
                for old, new in replacements.items():
                    content = content.replace(old, new)
                
                # Replace package statements for moved files
                if "/port/out/" in file_path.replace("\\", "/"):
                    content = content.replace("package realestate.server.application.realestate.domain;", "package realestate.server.application.realestate.application.port.out;")
                elif "/application/service/" in file_path.replace("\\", "/"):
                    content = content.replace("package realestate.server.application.realestate.application;", "package realestate.server.application.realestate.application.service;")
                elif "PblancBatchScheduler.java" in file_path:
                    content = content.replace("package realestate.server.application.realestate.application;", "package realestate.server.application.realestate.interfaces.scheduler;")
                    # Need to add imports for services in scheduler if they aren't fully qualified
                    content = content.replace("import realestate.server.application.realestate.application.service.AptPblancService;", "import realestate.server.application.realestate.application.service.AptPblancService;")
                
                # Save changes
                if content != original_content:
                    with open(file_path, 'w', encoding='utf-8') as f:
                        f.write(content)
                    print(f"Updated: {file_path}")
