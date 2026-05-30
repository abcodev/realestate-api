import csv
import psycopg2

conn = psycopg2.connect(
    host="localhost",
    port=5432,
    dbname="realestate",
    user="realestate",
    password="jlab1234"
)

sql = """
INSERT INTO real_estate_bgd_code
(bgd_code, bgd_name, abolish_status)
VALUES (%s, %s, %s)
"""

data = []

with open("realestate_bgd_code.csv", newline="", encoding="cp949") as f:
    reader = csv.reader(f)
    for row in reader:
        data.append((
            row[0],  # bgd_code
            row[1],  # bgd_name
            row[2]   # abolish_status
        ))

with conn.cursor() as cur:
    cur.executemany(sql, data)

conn.commit()
conn.close()

print("rows:", len(data))
print("first:", data[:1])

print(f"{len(data)}건 INSERT 완료")
