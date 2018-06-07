-- name: get-beer-styles-ordered-by-name
SELECT id_style AS id, name FROM beer_style
    ORDER BY name;
