-- name: get-tags-ordered-by-popularity
WITH all_tag_uses AS (
    SELECT tag.id_tag, name FROM tag INNER JOIN beer_tag ON beer_tag.id_tag = tag.id_tag
    UNION ALL
    SELECT tag.id_tag, name FROM tag INNER JOIN tag_brewery ON tag_brewery.id_tag = tag.id_tag)
SELECT id_tag as id, name FROM all_tag_uses
    GROUP BY id_tag, name
    ORDER BY count(id_tag) DESC
    LIMIT :first OFFSET :skip;
