-- name: get-beer-review
SELECT id_beer_review, cip, id_beer, title, content, image_path, rating, time FROM beer_review WHERE id_beer_review = :idBeerReview;

-- name: get-beer-review-thumbsups
SELECT "user".cip, name, surname FROM beer_review_user_thumbsup
    INNER JOIN "user" ON "user".cip = beer_review_user_thumbsup.cip
    WHERE id_beer_review = :idBeerReview

