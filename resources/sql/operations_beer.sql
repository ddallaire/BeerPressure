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


-- name: get-beers-ordered-by-name-asc
SELECT id_beer AS id, name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating
    ORDER BY name ASC
    LIMIT :first OFFSET :skip;

-- name: get-beers-ordered-by-name-desc
SELECT id_beer AS id, name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating
    ORDER BY name DESC
    LIMIT :first OFFSET :skip;


-- name: get-beers-filtered-by-breweries-ordered-by-name-asc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating AS beer
    INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer
    WHERE id_brewery IN (:breweries)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style
    ORDER BY name ASC
    LIMIT :first OFFSET :skip;

-- name: get-beers-filtered-by-breweries-ordered-by-name-desc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating AS beer
    INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer
    WHERE id_brewery IN (:breweries)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style
    ORDER BY name DESC
    LIMIT :first OFFSET :skip;


-- name: get-beers-filtered-by-tags-ordered-by-name-asc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating AS beer
    INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer
    WHERE id_tag IN (:tags)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style
    ORDER BY name ASC
    LIMIT :first OFFSET :skip;

-- name: get-beers-filtered-by-tags-ordered-by-name-desc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating AS beer
    INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer
    WHERE id_tag IN (:tags)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style
    ORDER BY name DESC
    LIMIT :first OFFSET :skip;


-- name: get-beers-filtered-by-breweries-tags-ordered-by-name-asc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating AS beer
    INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer
    INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer
    WHERE id_brewery IN (:breweries) AND id_tag IN (:tags)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style
    ORDER BY name ASC
    LIMIT :first OFFSET :skip;

-- name: get-beers-filtered-by-breweries-tags-ordered-by-name-desc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating AS beer
    INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer
    INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer
    WHERE id_brewery IN (:breweries) AND id_tag IN (:tags)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style
    ORDER BY name DESC
    LIMIT :first OFFSET :skip;


-- name: get-beers-ordered-by-rating-asc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating AS beer
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style
    ORDER BY rating ASC, name ASC
    LIMIT :first OFFSET :skip;

-- name: get-beers-ordered-by-rating-desc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating AS beer
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style
    ORDER BY rating DESC, name ASC
    LIMIT :first OFFSET :skip;


-- name: get-beers-filtered-by-breweries-ordered-by-rating-asc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating AS beer
    INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer
    WHERE id_brewery IN (:breweries)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style
    ORDER BY rating ASC, name ASC
    LIMIT :first OFFSET :skip;

-- name: get-beers-filtered-by-breweries-ordered-by-rating-desc
SELECT beer.id_beer AS id, name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating AS beer
    INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer
    WHERE id_brewery IN (:breweries)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style
    ORDER BY rating DESC, name ASC
    LIMIT :first OFFSET :skip;


-- name: get-beers-filtered-by-tags-ordered-by-rating-asc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating AS beer
    INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer
    WHERE id_tag IN (:tags)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style
    ORDER BY rating ASC, name ASC
    LIMIT :first OFFSET :skip;

-- name: get-beers-filtered-by-tags-ordered-by-rating-desc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating AS beer
    INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer
    WHERE id_tag IN (:tags)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style
    ORDER BY rating DESC, name ASC
    LIMIT :first OFFSET :skip;


-- name: get-beers-filtered-by-breweries-tags-ordered-by-rating-asc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating AS beer
    INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer
    INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer
    WHERE id_brewery IN (:breweries) AND id_tag IN (:tags)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style
    ORDER BY rating ASC, name ASC
    LIMIT :first OFFSET :skip;

-- name: get-beers-filtered-by-breweries-tags-ordered-by-rating-desc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating AS beer
    INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer
    INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer
    WHERE id_brewery IN (:breweries) AND id_tag IN (:tags)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style
    ORDER BY rating DESC, name ASC
    LIMIT :first OFFSET :skip;
