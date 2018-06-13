-- name: get-beer-review-comment
SELECT id_beer_review_comment, id_beer_review, cip, content, time FROM Beer_Review_Comment
WHERE id_beer_review_comment = :idBeerReviewComment;

-- name: get-beer-review-comments
SELECT id_beer_review_comment, id_beer_review, cip, content, time FROM Beer_Review_Comment
WHERE id_beer_review = :idBeerReview
LIMIT :first OFFSET :skip;

