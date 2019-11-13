package com.internship.utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

public class UtilsForDao {
    public static Order[] mapSortToOrder(List<String> sort, Root root, CriteriaBuilder cb) {
        return sort
                    .stream()
                    .map((str) -> {
                        String[] parts = str.split(":");
                        if (parts[1].equals("asc"))
                            return cb.asc(root.get(parts[0]));
                        return cb.desc(root.get(parts[0]));
                    })
                    .toArray(Order[]::new);
    }

    public static Predicate[] mapTaskFilterToPredicates(List<String> filter, Root root, CriteriaBuilder cb){
        return filter
                    .stream()
                    .map((str) -> {
                        String[] parts = str.split(":");
                        switch (parts[0]) {
                            case "isDone":
                                return cb.equal(root.get(parts[0]), Boolean.parseBoolean(parts[1]));
                            case "timeAdd":
                            case "deadLine":
                                return cb.equal(root.get(parts[0]), LocalDate.parse(parts[1]));
                            case "user":
                                return cb.equal(root.get("user"), Integer.valueOf(parts[1]));
                            default:
                                return cb.equal(root.get(parts[0]), parts[1]);
                    }
                })
                .toArray(Predicate[]::new);
    }

    public static Predicate[] mapMessagesFilterToPredicates(List<String> filter, Root root, CriteriaBuilder cb){
        return filter
                .stream()
                .map((str) -> {
                    String[] parts = str.split(":");
                    switch (parts[0]) {
                        case "isRead":
                            return cb.equal(root.get(parts[0]), Boolean.parseBoolean(parts[1]));
                        case "sendTime":
                            return cb.equal(root.get(parts[0]), LocalDate.parse(parts[1]));
                        case "senderUser":
                        case "receiverUser":
                            return cb.equal(root.get(parts[0]), Integer.valueOf(parts[1]));
                        default:
                            return cb.equal(root.get(parts[0]), parts[1]);
                    }
                })
                .toArray(Predicate[]::new);
    }

    public static Predicate[] mapFilterToPredicates(List<String> filter, Root root, CriteriaBuilder cb) {
        return filter
                    .stream()
                    .map((str) -> {
                        String[] parts = str.split(":");
                        return cb.equal(root.get(parts[0]), parts[1]);
                    })
                    .toArray(Predicate[]::new);
    }
}
