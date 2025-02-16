INSERT INTO account (id, balance)
VALUES (1, 1000)
ON CONFLICT (id) DO NOTHING;

INSERT INTO account (id, balance)
VALUES (2, 1000)
ON CONFLICT (id) DO NOTHING;

