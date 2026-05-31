ALTER TABLE rag_document
    ADD COLUMN IF NOT EXISTS source_type VARCHAR(50),
    ADD COLUMN IF NOT EXISTS source_id BIGINT;

CREATE UNIQUE INDEX IF NOT EXISTS uq_rag_document_source
    ON rag_document (source_type, source_id);
