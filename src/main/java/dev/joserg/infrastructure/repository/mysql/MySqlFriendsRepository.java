package dev.joserg.infrastructure.repository.mysql;

import dev.joserg.domain.friend.Friend;
import dev.joserg.domain.friend.FriendsRepository;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Singleton
@Transactional
public class MySqlFriendsRepository implements FriendsRepository {

    @Inject
    private DSLContext dslContext;

    @Override
    public Collection<Friend> all() {
        return dslContext
                .select(field("id"), field("name"))
                .from("friend")
                .fetch()
                .into(Friend.class);
    }

    @Override
    public Friend add(Friend friend) {
        dslContext
                .insertInto(table("friend"), field("id"), field("name"))
                .values(friend.id().toString(), friend.name())
                .execute();
        return friend;
    }

    @Override
    public Optional<Friend> find(UUID friendId) {
        return dslContext
                .select(field("id"), field("name"))
                .from("friend")
                .where(field("id").eq(friendId.toString()))
                .fetchOptional()
                .map(r -> r.into(Friend.class));
    }
}
