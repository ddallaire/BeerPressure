-- name: get-brewery
SELECT id_brewery AS id, name, description, image_path FROM brewery WHERE id_brewery = :id;
-- name: get-breweries
SELECT id_brewery AS id, name, description, image_path FROM brewery LIMIT :first OFFSET :skip;

