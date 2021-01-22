(ns telegram-chat-bot.commands.utils)

(defn extract-entity
  [body entity-type]
  (let [message          (or (:message body) (:edited_message body))
        text             (:text message)
        entities         (:entities message)
        entities-by-type (filter #(= (:type %) entity-type) entities)]
    (if (seq entities-by-type)
      (let [first-entity (first entities-by-type)
            offset       (:offset first-entity)
            length       (:length first-entity)]
        (subs text offset (+ offset length)))
      nil)))
