(use 'clj-webdriver.taxi)

;; Start up a browser
(set-driver! {:browser :firefox} "https://github.com")

(click "a[href*='login']")

(input-text "#login_field" "your-username")
(input-text "#password" "your-password")

(submit "#password")
(quit)