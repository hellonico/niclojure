(use 'clj-time.local)
(local-now)

(use 'clj-time.core)

; new date
(date-time 1998 1 2)

; time zone
(time-zone-for-offset -7)
(default-time-zone)

; compare dates
(after? (date-time 1986 10) (date-time 1986 9))

; dates arithmetic
(plus (date-time 1986 10 14) (months 1) (weeks 3))

; formatting
(use 'clj-time.format)
; available formatters
(show-formatters)

; parse
(def custom-formatter (formatter "yyyyMMdd"))
(parse custom-formatter "20100311")