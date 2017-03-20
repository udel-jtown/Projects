#lang racket

(require 2htdp/image
         (only-in mred make-bitmap bitmap-dc%)
         (only-in 2htdp/private/image-more render-image))

(provide collision?)

;; collision? Image Number Number Image Number Number -> Boolean
;; consumes: image1 is an image
;;           x1 is the x coordinate of the location where image1 will be drawn
;;           y1 is the x coordinate of the location where image1 will be drawn
;;           image2 is an image
;;           x2 is the x coordinate of the location where image2 will be drawn
;;           y2 is the x coordinate of the location where image2 will be drawn
;; produces: true if the first image collides with the second image
;;           false otherwise
(define (collision? image1 x1 y1 image2 x2 y2)
  (local [(define m1 (image->mask image1))
          (define w1 (image-width image1))
          (define h1 (vector-length m1))
          (define m2 (image->mask image2))
          (define w2 (image-width image2))          
          (define h2 (vector-length m2))
          (define dx (- (- x2 (floor (/ w2 2))) (- x1 (floor (/ w1 2)))))
          (define dy (- (- y2 (floor (/ h2 2))) (- y1 (floor (/ h1 2)))))]
     (for/or ((y (in-range (max 0 (- dy)) (min (- h1 dy) h2))))
      (not (zero? (bitwise-and (vector-ref m1 (+ y dy))
                               (arithmetic-shift (vector-ref m2 y) dx)))))))

(define (image->mask image)
  (define w (image-width image))
  (define h (image-height image))
  (define bm (make-bitmap w h))
  (define bdc (make-object bitmap-dc% bm))
  (render-image image bdc 0 0)
  (for/vector ((y (in-range h)))
    (define alpha-bytes 
      (make-bytes (* 4 w)))
    (send bdc get-argb-pixels 0 y w 1 alpha-bytes #t)
    (for/sum ((x (in-range w)))
      (if (zero? (bytes-ref alpha-bytes (* 4 x)))
          0
          (arithmetic-shift 1 x)))))

(define (mask-ref mask x y)
  (cond [(< y 0) 0]
        [(<= (vector-length mask) y) 0]
        [else
         (bitwise-and 1 (arithmetic-shift (vector-ref mask y) (- x)))]))

