-- name: get-beer-review
SELECT id_beer_review, cip, id_beer, title, content, image_path, rating, time FROM Beer_Review Where id_beer_review = :idBeerReview;

-- name: get-beer-reviews
SELECT id_beer_review, cip, id_beer, title, content, image_path, rating, time FROM Beer_Review
LIMIT :first OFFSET :skip;
