INSERT INTO acl_sid (id,        # primary key
                     principal, # is principal(like user) or authority(like role)
                     sid        # principal or authority name
                     )
VALUES (1, 1, 'manager'),
       (2, 1, 'hr'),
       (3, 0, 'ROLE_EDITOR');

INSERT INTO acl_class (id,
                       class    # acl domain object's class
                       )
VALUES (1, 'github.wzt3309.domain.NoticeMessage');

INSERT INTO acl_object_identity (id,                  # primary key
                                 object_id_class,     # reference to acl_class(id)
                                 object_id_identity,  # domain object's id reference to domain object table primary key(system_message(id))
                                 parent_object,       # parent object's id reference acl_object_identity(id)
                                 owner_sid,           # owner's sid reference to acl_sid(id)
                                 entries_inheriting   # can inherit authorities from parent object or not
                                 )
VALUES (1, 1, 1, NULl, 3, 0),
       (2, 1, 2, NULL, 3, 0),
       (3, 1, 3, NULL, 3, 0);

# used to store specific permission information
INSERT INTO acl_entry (id,                  # primary key
                       acl_object_identity, # reference to acl_object_identity
                       ace_order,           # ace order for the order of permission check
                       sid,                 # permission of whom for the object reference acl_sid
                       mask,                # mask corresponding to permission e.g. r(0001) w(0010) rw(0011)
                       granting,            # whether granting the permission represented by the mask
                       audit_success,       # about audit
                       audit_failure)
VALUES (1, 1, 1, 1, 1, 1, 1, 1),
       (2, 1, 2, 1, 2, 1, 1, 1),
       (3, 1, 3, 3, 1, 1, 1, 1),
       (4, 2, 1, 2, 1, 1, 1, 1),
       (5, 2, 2, 3, 1, 1, 1, 1),
       (6, 3, 1, 3, 1, 1, 1, 1),
       (7, 3, 2, 3, 2, 1, 1, 1);



INSERT INTO system_message(id, content)
VALUES (1, 'First Level Message'),
       (2, 'Second Level Message'),
       (3, 'Third Level Message');