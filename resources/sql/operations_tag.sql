-- name: get-tags-ordered-by-name-asc
SELECT id_tag as id, name FROM tag
    ORDER BY name ASC
    LIMIT :first OFFSET :skip;

-- name: get-tags-ordered-by-name-desc
SELECT id_tag as id, name FROM tag
    ORDER BY name DESC
    LIMIT :first OFFSET :skip;

-- name: get-tags-ordered-by-brewery-popularity-asc
SELECT tag.id_tag as id, name FROM tag
    LEFT JOIN tag_brewery ON tag_brewery.id_tag = tag.id_tag
    GROUP BY tag.id_tag, name
    ORDER BY count(tag.id_tag) ASC
    LIMIT :first OFFSET :skip;

-- name: get-tags-ordered-by-brewery-popularity-desc
SELECT tag.id_tag as id, name FROM tag
    LEFT JOIN tag_brewery ON tag_brewery.id_tag = tag.id_tag
    GROUP BY tag.id_tag, name
    ORDER BY count(tag.id_tag) DESC
    LIMIT :first OFFSET :skip;

-- name: get-tags-ordered-by-beer-popularity-asc
SELECT tag.id_tag as id, name FROM tag
    LEFT JOIN beer_tag ON beer_tag.id_tag = tag.id_tag
    GROUP BY tag.id_tag, name
    ORDER BY count(tag.id_tag) ASC
    LIMIT :first OFFSET :skip;


-- name: get-tags-ordered-by-beer-popularity-desc
SELECT tag.id_tag as id, name FROM tag
    LEFT JOIN beer_tag ON beer_tag.id_tag = tag.id_tag
    GROUP BY tag.id_tag, name
    ORDER BY count(tag.id_tag) DESC
    LIMIT :first OFFSET :skip;

-- name: get-tags-where-name
SELECT id_tag as id, name FROM tag
    WHERE name = :name;

-- name: insert-tag
INSERT INTO tag(name) VALUES
    (:name)
    RETURNING id_tag;
