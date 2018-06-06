-- name: get-brewery-review-comment
SELECT id_brewery_review_comment, cip, id_brewery_review, content, time FROM Brewery_Review_Comment
Where id_brewery_review_comment = :idBreweryReviewComment;

-- name: get-brewery-review-comments
SELECT id_brewery_review_comment, cip, id_brewery_review, content, time FROM Brewery_Review_Comment
Where id_brewery_review = :idBreweryReview
LIMIT :first OFFSET :skip;
