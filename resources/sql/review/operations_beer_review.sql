-- name: get-beer-review
SELECT id_beer_review, "user".cip, name, surname, id_beer, title, content, image_path, rating, time FROM beer_review
    INNER JOIN "user" ON "user".cip = beer_review.cip
    WHERE id_beer_review = :idBeerReview;

-- name: get-beer-review-thumbsups
SELECT "user".cip, name, surname FROM beer_review_user_thumbsup
    INNER JOIN "user" ON "user".cip = beer_review_user_thumbsup.cip
    WHERE id_beer_review = :idBeerReview;

-- name: insert-beer-review-thumbsup!
INSERT INTO beer_review_user_thumbsup(id_beer_review, cip) VALUES(:id, :cip);

-- name: delete-beer-review-thumbsup!
DELETE FROM beer_review_user_thumbsup WHERE id_beer_review = :id AND cip = :cip;
