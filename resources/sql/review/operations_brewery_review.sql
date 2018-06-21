-- name: get-brewery-review
SELECT id_brewery_review, "user".cip, name, surname, id_brewery, title, content, image_path, rating, time FROM brewery_review
    INNER JOIN "user" ON "user".cip = brewery_review.cip
    WHERE id_brewery_review = :idBreweryReview;

-- name: get-brewery-review-thumbsups
SELECT "user".cip, name, surname FROM brewery_review_user_thumbsup
    INNER JOIN "user" ON "user".cip = brewery_review_user_thumbsup.cip
    WHERE id_brewery_review = :idBreweryReview;

-- name: insert-brewery-review
INSERT INTO brewery_review(cip, id_brewery, title, content, image_path, rating, time) VALUES
    (:cip, :idBrewery, :title, :content, :imagePath, :rating, now())
    RETURNING id_brewery_review;

-- name: update-brewery-review!
UPDATE brewery_review SET title = :title, content = :content, image_path = :imagePath, rating = :rating
    WHERE id_brewery_review = :idBreweryReview AND cip = :cip;

-- name: delete-brewery-review!
DELETE FROM brewery_review WHERE id_brewery_review = :id AND cip = :cip;

-- name: insert-brewery-review-thumbsup!
INSERT INTO brewery_review_user_thumbsup(id_brewery_review, cip) VALUES(:id, :cip);

-- name: delete-brewery-review-thumbsup!
DELETE FROM brewery_review_user_thumbsup WHERE id_brewery_review = :id AND cip = :cip;
