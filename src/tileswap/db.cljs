(ns tileswap.db)

(def default-db
  {:puzzle {:difficulty 4
            :order (into [] (shuffle (range 16)))
            :selected   nil
            :movers     []
            :shift      nil
            :transition true}
   :img    {:url    nil
            :width  nil
            :height nil}
   :screen {:width        js/window.innerWidth
            :height       js/window.innerHeight
            :puzzle-scale nil
            :ui-scale     (/ 1 48)
            :img-scale    (/ 6 8)}})
