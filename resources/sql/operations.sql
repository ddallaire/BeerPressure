-- name: get-brewery
SELECT id_brewery AS id, name, description, image_path AS imagePath FROM brewery WHERE id = :id;
-- name: get-breweries
SELECT id_brewery AS id, name, description, image_path AS imagePath FROM brewery;

-- name: get-beer
SELECT id_beer AS id, name, description, ibu, alcool_percent as alcoholPercent, image_path AS imagePath FROM beer WHERE id = :id;
-- name: get-beers
SELECT id_beer AS id, name, description, ibu, alcool_percent as alcoholPercent, image_path AS imagePath FROM beer;
