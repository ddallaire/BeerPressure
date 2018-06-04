-- name: get-brewery-tags
SELECT tag.id_tag as id, name FROM tag
    INNER JOIN tag_brewery ON tag_brewery.id_tag = tag.id_tag
    WHERE id_brewery = :id;


-- name: get-brewery
SELECT id_brewery AS id, name, description, image_path FROM brewery WHERE id_brewery = :id;


-- name: get-breweries-ordered-by-name-asc
SELECT id_brewery AS id, name, description, image_path FROM brewery
    ORDER BY name ASC
    LIMIT :first OFFSET :skip;

-- name: get-breweries-ordered-by-name-desc
SELECT id_brewery AS id, name, description, image_path FROM brewery
    ORDER BY name DESC
    LIMIT :first OFFSET :skip;


-- name: get-breweries-filtered-by-tags-ordered-by-name-asc
SELECT brewery.id_brewery AS id, name, description, image_path FROM brewery
    INNER JOIN tag_brewery ON tag_brewery.id_brewery = brewery.id_brewery
    WHERE id_tag IN (:tags)
    GROUP BY brewery.id_brewery, name, description, image_path
    ORDER BY name ASC
    LIMIT :first OFFSET :skip;

-- name: get-breweries-filtered-by-tags-ordered-by-name-desc
SELECT brewery.id_brewery AS id, name, description, image_path FROM brewery
    INNER JOIN tag_brewery ON tag_brewery.id_brewery = brewery.id_brewery
    WHERE id_tag IN (:tags)
    GROUP BY brewery.id_brewery, name, description, image_path
    ORDER BY name DESC
    LIMIT :first OFFSET :skip;


-- name: get-breweries-ordered-by-rating-asc
WITH all_brewery_ratings AS (
    SELECT id_brewery, rating FROM brewery_review
    UNION ALL
    SELECT id_brewery, rating FROM brewery_user_rating
)
SELECT brewery.id_brewery AS id, name, description, image_path FROM brewery
    LEFT JOIN all_brewery_ratings ON all_brewery_ratings.id_brewery = brewery.id_brewery
    GROUP BY brewery.id_brewery, name, description, image_path
    ORDER BY avg(rating) ASC, name ASC
    LIMIT :first OFFSET :skip;

-- name: get-breweries-ordered-by-rating-desc
WITH all_brewery_ratings AS (
    SELECT id_brewery, rating FROM brewery_review
    UNION ALL
    SELECT id_brewery, rating FROM brewery_user_rating
)
SELECT brewery.id_brewery AS id, name, description, image_path FROM brewery
    LEFT JOIN all_brewery_ratings ON all_brewery_ratings.id_brewery = brewery.id_brewery
    GROUP BY brewery.id_brewery, name, description, image_path
    ORDER BY avg(rating) DESC, name ASC
    LIMIT :first OFFSET :skip;


-- name: get-breweries-filtered-by-tags-ordered-by-rating-asc
WITH all_brewery_ratings AS (
    SELECT id_brewery, rating FROM brewery_review
    UNION ALL
    SELECT id_brewery, rating FROM brewery_user_rating
)
SELECT brewery.id_brewery AS id, name, description, image_path FROM brewery
    INNER JOIN tag_brewery ON tag_brewery.id_brewery = brewery.id_brewery
    LEFT JOIN all_brewery_ratings ON all_brewery_ratings.id_brewery = brewery.id_brewery
    WHERE id_tag IN (:tags)
    GROUP BY brewery.id_brewery, name, description, image_path
    ORDER BY avg(rating) ASC, name ASC
    LIMIT :first OFFSET :skip;

-- name: get-breweries-filtered-by-tags-ordered-by-rating-desc
WITH all_brewery_ratings AS (
    SELECT id_brewery, rating FROM brewery_review
    UNION ALL
    SELECT id_brewery, rating FROM brewery_user_rating
)
SELECT brewery.id_brewery AS id, name, description, image_path FROM brewery
    INNER JOIN tag_brewery ON tag_brewery.id_brewery = brewery.id_brewery
    LEFT JOIN all_brewery_ratings ON all_brewery_ratings.id_brewery = brewery.id_brewery
    WHERE id_tag IN (:tags)
    GROUP BY brewery.id_brewery, name, description, image_path
    ORDER BY avg(rating) DESC, name ASC
    LIMIT :first OFFSET :skip;

