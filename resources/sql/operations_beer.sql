-- name: get-beer
id_beer AS id, name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating WHERE id_beer = :id;
