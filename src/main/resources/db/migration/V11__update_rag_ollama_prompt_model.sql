UPDATE ai_prompt_template
SET model = 'qwen3:8b',
    updated_at = now()
WHERE entity_type = 'RAG_REALESTATE'
  AND ai_provider = 'OLLAMA'
  AND is_active = true;
