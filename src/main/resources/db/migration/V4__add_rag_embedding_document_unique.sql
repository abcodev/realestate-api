CREATE UNIQUE INDEX IF NOT EXISTS uq_rag_embedding_document
    ON rag_embedding (document_id);
