;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname |Project 2 Server|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f ())))
;; Project 2
;; 2e, Chat Noir
;; Amelia Hamilton
;; John Townsend
;; John Roehsler
(require 2htdp/image)
(require 2htdp/universe)
;; Messages
;; -- "End Turn", a stop message FROM a client
;; -- "New Turn", a go message TO a client

;; Bundle is
;;   (make-bundle UniverseState [Listof mail?] [Listof iworld?])

;; Result is
;;   (make-bundle [Listof iworld?]
;;                (list (make-mail iworld? GoMessage))
;;                '())

;; add-world: [ListOf Iworld] Iworld -> Result
;; add world iw to the universe, when server is in state u
(check-expect
 (add-world empty iworld1)
 (make-bundle (list iworld1)
              (list (make-mail iworld1 "New Turn"))
              '()))

(define (add-world univ wrld)
  (local ((define univ* (append univ (list wrld))))
    (make-bundle univ*
                 (list (make-mail (first univ*) "New Turn"))
                 empty)))

;; switch: [ListOf Iworld] Iworld "End Turn" -> Result
;; world iw sent message m when server is in state u
(check-expect
 (switch (list iworld1 iworld2) iworld1 "End Turn")
 (make-bundle (list iworld2 iworld1)
              (list (make-mail iworld2 "New Turn"))
              empty))
(define (switch univ wrld m)
  (local ((define univ* (append (rest univ) (list (first univ)))))
    (make-bundle univ*
                 (list (make-mail (first univ*) "New Turn"))
                 empty)))

(define (start-server)
  (universe empty 
            (on-new add-world) 
            (on-msg switch)))
(start-server)
