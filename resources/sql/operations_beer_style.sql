-- name: get-beer-styles-ordered-by-name-asc
SELECT id_style AS id, name FROM beer_style
    ORDER BY name ASC
    LIMIT :first OFFSET :skip;

-- name: get-beer-styles-ordered-by-name-desc
SELECT id_style AS id, name FROM beer_style
    ORDER BY name DESC
    LIMIT :first OFFSET :skip;
