; https://gist.github.com/1074844

(use 'calx)
(def benchn 40)

(def ocl-src "int fib(int a) {
int b = -1;
int c = 1;
int d;
int i;
for (i=0;i<=a;i++) {
d = c + b;
b = c;
c = d;
}
return c;
}
__kernel void fibs(__global const int *a, __global int *b) {
int gid = get_global_id(0);
b[gid] = fib(a[gid]);
}")

(defn ocl-fibs [maxn prog]
  (concat '(0 1) @(with-program prog
    (let [r (vec (range 2 (inc maxn)))
          a (wrap r :int32)
          b (mimic a)]
      (enqueue-kernel :fibs (count r) a b)
      (enqueue-read b)))))

(prn (available-devices))

; not including compilation time

(println "OpenCL CPU")
(with-cpu
  (let [prog (compile-program ocl-src)]
    (time (prn (ocl-fibs benchn prog)))))

(println "")
(println "OpenCL GPU")
(with-gpu
  (let [prog (compile-program ocl-src)]
    (time (prn (ocl-fibs benchn prog)))))

; -------------------

(defn clj-a-fib [n]
  (let [b (ref -1)
        c (ref 1)
        d (ref 0)]
    (dosync
      (dotimes [_ n]
        (ref-set d (+ @c @b))
        (ref-set b @c)
        (ref-set c @d)))
    @c))

(defn clj-a-fibs [maxn]
  (pmap clj-a-fib (range 1 (+ 2 maxn))))

(println "")
(println "Clojure Ref")
(time (prn (clj-a-fibs benchn)))

(defn clj-r-fib [n]
  (condp = n 0 0 1 1
    (+ (clj-r-fib (dec n)) (clj-r-fib (- n 2)))))

(defn clj-r-fibs [maxn]
  (pmap clj-r-fib (range 0 (inc maxn))))

(println "")
(println "Clojure Recursive")
(println "Press Ctrl+C to stop ;-)")
(time (prn (clj-r-fibs benchn)))