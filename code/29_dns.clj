(use 'clj-dns.core)
(import 'org.xbill.DNS.Type)

; regular lookup
(pprint (:answers (dns-lookup "www.google.com" Type/A)))

; reverse lookup
(reverse-dns-lookup "173.194.38.113")