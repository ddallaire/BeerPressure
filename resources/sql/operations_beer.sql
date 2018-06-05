-- name: get-beer-breweries
SELECT brewery.id_brewery AS id, name, description, image_path FROM brewery
    INNER JOIN beer_brewery ON beer_brewery.id_brewery = brewery.id_brewery
    WHERE id_beer = :id;

-- name: get-beer-tags
SELECT tag.id_tag as id, name FROM tag
    INNER JOIN beer_tag ON beer_tag.id_tag = tag.id_tag
    WHERE id_beer = :id;


-- name: get-beer
SELECT id_beer AS id, name, description, ibu, alcohol_percent, image_path FROM beer WHERE id_beer = :id;


-- name: get-beers-ordered-by-name-asc
SELECT id_beer AS id, name, description, ibu, alcohol_percent, image_path FROM beer
    ORDER BY name ASC
    LIMIT :first OFFSET :skip;

-- name: get-beers-ordered-by-name-desc
SELECT id_beer AS id, name, description, ibu, alcohol_percent, image_path FROM beer
    ORDER BY name DESC
    LIMIT :first OFFSET :skip;


-- name: get-beers-filtered-by-breweries-ordered-by-name-asc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path FROM beer
    INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer
    WHERE id_brewery IN (:breweries)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path
    ORDER BY name ASC
    LIMIT :first OFFSET :skip;

-- name: get-beers-filtered-by-breweries-ordered-by-name-desc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path FROM beer
    INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer
    WHERE id_brewery IN (:breweries)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path
    ORDER BY name DESC
    LIMIT :first OFFSET :skip;


-- name: get-beers-filtered-by-tags-ordered-by-name-asc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path FROM beer
    INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer
    WHERE id_tag IN (:tags)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path
    ORDER BY name ASC
    LIMIT :first OFFSET :skip;

-- name: get-beers-filtered-by-tags-ordered-by-name-desc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path FROM beer
    INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer
    WHERE id_tag IN (:tags)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path
    ORDER BY name DESC
    LIMIT :first OFFSET :skip;


-- name: get-beers-filtered-by-breweries-tags-ordered-by-name-asc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path FROM beer
    INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer
    INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer
    WHERE id_brewery IN (:breweries) AND id_tag IN (:tags)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path
    ORDER BY name ASC
    LIMIT :first OFFSET :skip;

-- name: get-beers-filtered-by-breweries-tags-ordered-by-name-desc
SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path FROM beer
    INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer
    INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer
    WHERE id_brewery IN (:breweries) AND id_tag IN (:tags)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path
    ORDER BY name DESC
    LIMIT :first OFFSET :skip;


-- name: get-beers-ordered-by-rating-asc
WITH all_beer_ratings AS (
    SELECT id_beer, 0.0 as rating FROM beer
    UNION ALL
    SELECT id_beer, rating FROM beer_review
    UNION ALL
    SELECT id_beer, rating FROM beer_user_rating
)
SELECT beer.id_beer AS id, name, description, ibu, alcohol_percent, image_path FROM beer
    LEFT JOIN all_beer_ratings ON all_beer_ratings.id_beer = beer.id_beer
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path
    ORDER BY avg(rating) ASC, name ASC
    LIMIT :first OFFSET :skip;

-- name: get-beers-ordered-by-rating-desc
WITH all_beer_ratings AS (
    SELECT id_beer, 0.0 as rating FROM beer
    UNION ALL
    SELECT id_beer, rating FROM beer_review
    UNION ALL
    SELECT id_beer, rating FROM beer_user_rating
)
SELECT beer.id_beer AS id, name, description, ibu, alcohol_percent, image_path FROM beer
    LEFT JOIN all_beer_ratings ON all_beer_ratings.id_beer = beer.id_beer
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path
    ORDER BY avg(rating) DESC, name ASC
    LIMIT :first OFFSET :skip;


-- name: get-beers-filtered-by-breweries-ordered-by-rating-asc
WITH all_beer_ratings AS (
    SELECT id_beer, 0.0 as rating FROM beer
    UNION ALL
    SELECT id_beer, rating FROM beer_review
    UNION ALL
    SELECT id_beer, rating FROM beer_user_rating
)
SELECT beer.id_beer AS id, name, description, ibu, alcohol_percent, image_path FROM beer
    LEFT JOIN all_beer_ratings ON all_beer_ratings.id_beer = beer.id_beer
    INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer
    WHERE id_brewery IN (:breweries)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path
    ORDER BY avg(rating) ASC, name ASC
    LIMIT :first OFFSET :skip;

-- name: get-beers-filtered-by-breweries-ordered-by-rating-desc
WITH all_beer_ratings AS (
    SELECT id_beer, 0.0 as rating FROM beer
    UNION ALL
    SELECT id_beer, rating FROM beer_review
    UNION ALL
    SELECT id_beer, rating FROM beer_user_rating
)
SELECT beer.id_beer AS id, name, description, ibu, alcohol_percent, image_path FROM beer
    LEFT JOIN all_beer_ratings ON all_beer_ratings.id_beer = beer.id_beer
    INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer
    WHERE id_brewery IN (:breweries)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path
    ORDER BY avg(rating) DESC, name ASC
    LIMIT :first OFFSET :skip;


-- name: get-beers-filtered-by-tags-ordered-by-rating-asc
WITH all_beer_ratings AS (
    SELECT id_beer, 0.0 as rating FROM beer
    UNION ALL
    SELECT id_beer, rating FROM beer_review
    UNION ALL
    SELECT id_beer, rating FROM beer_user_rating
)
SELECT beer.id_beer AS id, name, description, ibu, alcohol_percent, image_path FROM beer
    LEFT JOIN all_beer_ratings ON all_beer_ratings.id_beer = beer.id_beer
    INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer
    WHERE id_tag IN (:tags)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path
    ORDER BY avg(rating) ASC, name ASC
    LIMIT :first OFFSET :skip;

-- name: get-beers-filtered-by-tags-ordered-by-rating-desc
WITH all_beer_ratings AS (
    SELECT id_beer, 0.0 as rating FROM beer
    UNION ALL
    SELECT id_beer, rating FROM beer_review
    UNION ALL
    SELECT id_beer, rating FROM beer_user_rating
)
SELECT beer.id_beer AS id, name, description, ibu, alcohol_percent, image_path FROM beer
    LEFT JOIN all_beer_ratings ON all_beer_ratings.id_beer = beer.id_beer
    INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer
    WHERE id_tag IN (:tags)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path
    ORDER BY avg(rating) DESC, name ASC
    LIMIT :first OFFSET :skip;


-- name: get-beers-filtered-by-breweries-tags-ordered-by-rating-asc
WITH all_beer_ratings AS (
    SELECT id_beer, 0.0 as rating FROM beer
    UNION ALL
    SELECT id_beer, rating FROM beer_review
    UNION ALL
    SELECT id_beer, rating FROM beer_user_rating
)
SELECT beer.id_beer AS id, name, description, ibu, alcohol_percent, image_path FROM beer
    LEFT JOIN all_beer_ratings ON all_beer_ratings.id_beer = beer.id_beer
    INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer
    INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer
    WHERE id_brewery IN (:breweries) AND id_tag IN (:tags)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path
    ORDER BY avg(rating) ASC, name ASC
    LIMIT :first OFFSET :skip;

-- name: get-beers-filtered-by-breweries-tags-ordered-by-rating-desc
WITH all_beer_ratings AS (
    SELECT id_beer, 0.0 as rating FROM beer
    UNION ALL
    SELECT id_beer, rating FROM beer_review
    UNION ALL
    SELECT id_beer, rating FROM beer_user_rating
)
SELECT beer.id_beer AS id, name, description, ibu, alcohol_percent, image_path FROM beer
    LEFT JOIN all_beer_ratings ON all_beer_ratings.id_beer = beer.id_beer
    INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer
    INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer
    WHERE id_brewery IN (:breweries) AND id_tag IN (:tags)
    GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path
    ORDER BY avg(rating) DESC, name ASC
    LIMIT :first OFFSET :skip;
