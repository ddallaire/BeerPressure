-- name: get-tags-ordered-by-name
SELECT id_tag as id, name FROM tag
    ORDER BY name
    LIMIT :first OFFSET :skip;

-- name: get-tags-ordered-by-brewery-popularity
SELECT tag.id_tag as id, name FROM tag
    LEFT JOIN tag_brewery ON tag_brewery.id_tag = tag.id_tag
    GROUP BY tag.id_tag, name
    ORDER BY count(tag.id_tag) DESC
    LIMIT :first OFFSET :skip;

-- name: get-tags-ordered-by-beer-popularity
SELECT tag.id_tag as id, name FROM tag
    LEFT JOIN beer_tag ON beer_tag.id_tag = tag.id_tag
    GROUP BY tag.id_tag, name
    ORDER BY count(tag.id_tag) DESC
    LIMIT :first OFFSET :skip;
