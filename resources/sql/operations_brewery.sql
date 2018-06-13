-- name: get-brewery
SELECT id_brewery AS id, name, description, image_path, rating FROM brewery_with_rating WHERE id_brewery = :id;
