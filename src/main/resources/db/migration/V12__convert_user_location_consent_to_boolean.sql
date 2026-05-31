ALTER TABLE users
    ALTER COLUMN location_consent_enabled TYPE boolean
    USING CASE
        WHEN location_consent_enabled IS NULL THEN false
        WHEN lower(trim(location_consent_enabled::text)) IN ('true', 't', '1', 'y', 'yes') THEN true
        ELSE false
    END;

ALTER TABLE users
    ALTER COLUMN location_consent_enabled SET DEFAULT false;
