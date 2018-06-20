-- name: get-brewery-review
SELECT id_brewery_review, "user".cip, name, surname, id_brewery, title, content, image_path, rating, time FROM brewery_review
    INNER JOIN "user" ON "user".cip = brewery_review.cip
    WHERE id_brewery_review = :idBreweryReview;

-- name: get-brewery-review-thumbsups
SELECT "user".cip, name, surname FROM brewery_review_user_thumbsup
    INNER JOIN "user" ON "user".cip = brewery_review_user_thumbsup.cip
    WHERE id_brewery_review = :idBreweryReview
