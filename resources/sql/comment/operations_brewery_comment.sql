-- name: get-brewery-review-comment
SELECT id_brewery_review_comment, cip, id_brewery_review, content, time FROM brewery_review_comment
WHERE id_brewery_review_comment = :idBreweryReviewComment;
