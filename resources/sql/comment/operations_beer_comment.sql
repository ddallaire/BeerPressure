-- name: get-beer-review-comment
SELECT id_beer_review_comment, id_beer_review, cip, content, time FROM beer_review_comment
WHERE id_beer_review_comment = :idBeerReviewComment;
