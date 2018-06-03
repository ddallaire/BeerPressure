-- name: get-brewery
SELECT id_brewery AS id, name, description, image_path FROM brewery WHERE id_brewery = :id;
-- name: get-breweries
SELECT id_brewery AS id, name, description, image_path FROM brewery LIMIT :first OFFSET :skip;
-- name: get-beer-breweries
SELECT brewery.id_brewery AS id, name, description, image_path FROM brewery
    INNER JOIN beer_brewery ON beer_brewery.id_brewery = brewery.id_brewery
    WHERE id_beer = :id;

-- name: get-beer
SELECT id_beer AS id, name, description, ibu, alcohol_percent, image_path FROM beer WHERE id_beer = :id;
-- name: get-beers
SELECT id_beer AS id, name, description, ibu, alcohol_percent, image_path FROM beer LIMIT :first OFFSET :skip;
