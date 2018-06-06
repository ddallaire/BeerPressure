-- name: get-brewery-review
SELECT id_brewery_review, cip, id_brewery, title, content, image_path, rating, time FROM Brewery_Review
Where id_brewery_review = :idBreweryReview;

-- name: get-brewery-reviews
SELECT id_brewery_review, cip, id_brewery, title, content, image_path, rating, time FROM Brewery_Review
LIMIT :first OFFSET :skip;
