-- name: get-brewery
SELECT id_brewery AS id, name, description, image_path FROM brewery WHERE id_brewery = :id;
-- name: get-breweries
SELECT id_brewery AS id, name, description, image_path FROM brewery;

-- name: get-beer
SELECT id_beer AS id, name, description, ibu, alcohol_percent, image_path FROM beer WHERE id_beer = :id;
-- name: get-beers
SELECT id_beer AS id, name, description, ibu, alcohol_percent, image_path FROM beer;
