-- name: get-brewery-tags
SELECT tag.id_tag AS id, name FROM tag
    INNER JOIN tag_brewery ON tag_brewery.id_tag = tag.id_tag
    WHERE id_brewery = :id;


-- name: get-brewery
SELECT id_brewery AS id, name, description, image_path, rating FROM brewery_with_rating WHERE id_brewery = :id;


-- name: get-breweries-ordered-by-name-asc
SELECT id_brewery AS id, name, description, image_path, rating FROM brewery_with_rating
    ORDER BY name ASC
    LIMIT :first OFFSET :skip;

-- name: get-breweries-ordered-by-name-desc
SELECT id_brewery AS id, name, description, image_path, rating FROM brewery_with_rating
    ORDER BY name DESC
    LIMIT :first OFFSET :skip;


-- name: get-breweries-filtered-by-tags-ordered-by-name-asc
SELECT brewery.id_brewery AS id, name, description, image_path, rating FROM brewery_with_rating AS brewery
    INNER JOIN tag_brewery ON tag_brewery.id_brewery = brewery.id_brewery
    WHERE id_tag IN (:tags)
    GROUP BY brewery.id_brewery, name, description, image_path, rating
    ORDER BY name ASC
    LIMIT :first OFFSET :skip;

-- name: get-breweries-filtered-by-tags-ordered-by-name-desc
SELECT brewery.id_brewery AS id, name, description, image_path, rating FROM brewery_with_rating AS brewery
    INNER JOIN tag_brewery ON tag_brewery.id_brewery = brewery.id_brewery
    WHERE id_tag IN (:tags)
    GROUP BY brewery.id_brewery, name, description, image_path, rating
    ORDER BY name DESC
    LIMIT :first OFFSET :skip;


-- name: get-breweries-ordered-by-rating-asc
SELECT id_brewery AS id, name, description, image_path, rating FROM brewery_with_rating
    ORDER BY rating ASC, name ASC
    LIMIT :first OFFSET :skip;

-- name: get-breweries-ordered-by-rating-desc
SELECT id_brewery AS id, name, description, image_path, rating FROM brewery_with_rating
    ORDER BY rating DESC, name ASC
    LIMIT :first OFFSET :skip;


-- name: get-breweries-filtered-by-tags-ordered-by-rating-asc
SELECT brewery.id_brewery AS id, name, description, image_path, rating FROM brewery_with_rating AS brewery
    INNER JOIN tag_brewery ON tag_brewery.id_brewery = brewery.id_brewery
    WHERE id_tag IN (:tags)
    GROUP BY brewery.id_brewery, name, description, image_path, rating
    ORDER BY rating ASC, name ASC
    LIMIT :first OFFSET :skip;

-- name: get-breweries-filtered-by-tags-ordered-by-rating-desc
SELECT brewery.id_brewery AS id, name, description, image_path, rating FROM brewery_with_rating AS brewery
    INNER JOIN tag_brewery ON tag_brewery.id_brewery = brewery.id_brewery
    WHERE id_tag IN (:tags)
    GROUP BY brewery.id_brewery, name, description, image_path, rating
    ORDER BY rating DESC, name ASC
    LIMIT :first OFFSET :skip;
