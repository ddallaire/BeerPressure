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

-- name: insert-beer
INSERT INTO beer(name, description, ibu, alcohol_percent, image_path, id_style) VALUES
    (:name, :description, :ibu, :alcoholPercent, :imagePath, :style)
    RETURNING id_beer;

-- name: update-beer!
UPDATE beer SET name = :name, description = :description, ibu=:ibu, alcohol_percent=:alcoholPercent, image_path = :imagePath, id_style=:style
   WHERE id_beer = :id;

-- name: delete-beer-breweries!
DELETE FROM beer_brewery WHERE id_beer = :id;

-- name: delete-beer-tags!
DELETE FROM beer_tag WHERE id_beer = :id;

-- name: delete-beer!
DELETE FROM beer WHERE id_beer = :id;
