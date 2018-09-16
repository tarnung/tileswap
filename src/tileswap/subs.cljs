(ns tileswap.subs
  (:require
    [re-frame.core :as rf]))

(rf/reg-sub
  ::difficulty
  (fn [db]
    (-> db :puzzle :difficulty)))

(rf/reg-sub
  ::order
  (fn [db]
    (-> db :puzzle :order)))

(rf/reg-sub
  ::selected
  (fn [db]
    (-> db :puzzle :selected)))

(rf/reg-sub
  ::transition
  (fn [db]
    (-> db :puzzle :transition)))

(rf/reg-sub
  ::shift
  (fn [db]
    (-> db :puzzle :shift)))

(rf/reg-sub
  ::movers
  (fn [db]
    (-> db :puzzle :movers)))

(rf/reg-sub
  ::img-url
  (fn [db]
    (-> db :img :url)))

(rf/reg-sub
  ::img-width
  (fn [db]
    (-> db :img :width)))

(rf/reg-sub
  ::img-height
  (fn [db]
    (-> db :img :height)))

(rf/reg-sub
  ::puzzle-scale
  (fn [db]
    (-> db :screen :puzzle-scale)))

(rf/reg-sub
  ::ui-scale
  (fn [db]
    (-> db :screen :ui-scale)))

(rf/reg-sub
  ::img-scale
  (fn [db]
    (-> db :screen :img-scale)))

(rf/reg-sub
  ::screen-width
  (fn [db]
    (-> db :screen :width)))

(rf/reg-sub
  ::screen-height
  (fn [db]
    (-> db :screen :height)))

(rf/reg-sub
  ::ui-size
  (fn [_]
    [(rf/subscribe [::screen-width])
     (rf/subscribe [::screen-height])
     (rf/subscribe [::puzzle-scale])
     (rf/subscribe [::ui-scale])])
  (fn [[screen-width screen-height puzzle-scale ui-scale]]
    (* (min screen-height screen-width)
       puzzle-scale
       ui-scale)))

(rf/reg-sub
  ::scaled-img-size
  (fn [_]
    [(rf/subscribe [::img-width])
     (rf/subscribe [::img-height])
     (rf/subscribe [::puzzle-scale])
     (rf/subscribe [::img-scale])])
  (fn [[img-width img-height puzzle-scale img-scale]]
    (let [scaled-img-width (* img-width
                              puzzle-scale
                              img-scale)
          scaled-img-height (* img-height
                               puzzle-scale
                               img-scale)]
      [scaled-img-width scaled-img-height])))

(rf/reg-sub
  ::tile-size
  (fn [_]
    [(rf/subscribe [::scaled-img-size])
     (rf/subscribe [::difficulty])])
  (fn [[scaled-img-size difficulty]]
    (let [[scaled-img-width scaled-img-height] scaled-img-size
          tile-width (/ scaled-img-width difficulty)
          tile-height (/ scaled-img-height difficulty)]
      [tile-width tile-height])))
