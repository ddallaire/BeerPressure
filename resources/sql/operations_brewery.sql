-- name: get-brewery-tags
SELECT tag.id_tag AS id, name FROM tag
    INNER JOIN tag_brewery ON tag_brewery.id_tag = tag.id_tag
    WHERE id_brewery = :id;


-- name: get-brewery
SELECT id_brewery AS id, name, description, image_path, rating FROM brewery_with_rating WHERE id_brewery = :id;
