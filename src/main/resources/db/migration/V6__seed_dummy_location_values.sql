INSERT INTO countries (name, iso_code, created_at, updated_at)
SELECT 'Dummy Country', 'DUM', NOW(6), NOW(6)
WHERE NOT EXISTS (
    SELECT 1 FROM countries WHERE iso_code = 'DUM'
);

INSERT INTO states (country_id, name, created_at, updated_at)
SELECT c.id, 'Dummy State', NOW(6), NOW(6)
FROM countries c
WHERE c.iso_code = 'DUM'
  AND NOT EXISTS (
      SELECT 1
      FROM states s
      WHERE s.country_id = c.id
        AND s.name = 'Dummy State'
  );

INSERT INTO cities (state_id, name, created_at, updated_at)
SELECT s.id, 'Dummy City', NOW(6), NOW(6)
FROM states s
JOIN countries c ON c.id = s.country_id
WHERE c.iso_code = 'DUM'
  AND s.name = 'Dummy State'
  AND NOT EXISTS (
      SELECT 1
      FROM cities ct
      WHERE ct.state_id = s.id
        AND ct.name = 'Dummy City'
  );

UPDATE tenants t
JOIN countries c ON c.iso_code = 'DUM'
JOIN states s ON s.country_id = c.id AND s.name = 'Dummy State'
JOIN cities ct ON ct.state_id = s.id AND ct.name = 'Dummy City'
SET
    t.country_id = COALESCE(t.country_id, c.id),
    t.state_id = COALESCE(t.state_id, s.id),
    t.city_id = COALESCE(t.city_id, ct.id),
    t.contact_phone = COALESCE(t.contact_phone, '9800000000'),
    t.address_line1 = COALESCE(t.address_line1, 'Dummy Address'),
    t.postal_code = COALESCE(t.postal_code, '00000'),
    t.updated_at = NOW(6)
WHERE t.country_id IS NULL
   OR t.state_id IS NULL
   OR t.city_id IS NULL
   OR t.contact_phone IS NULL
   OR t.address_line1 IS NULL
   OR t.postal_code IS NULL;
