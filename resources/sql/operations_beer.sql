-- name: get-beer-breweries
SELECT brewery.id_brewery AS id, name, description, image_path, rating FROM brewery_with_rating AS brewery
    INNER JOIN beer_brewery ON beer_brewery.id_brewery = brewery.id_brewery
    WHERE id_beer = :id;

-- name: get-beer-tags
SELECT tag.id_tag AS id, name FROM tag
    INNER JOIN beer_tag ON beer_tag.id_tag = tag.id_tag
    WHERE id_beer = :id;

-- name: get-beer
SELECT id_beer AS id, name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating WHERE id_beer = :id;
