-- Drop the plain index and replace with a unique constraint on action identity fields.
-- This prevents duplicate action rows under concurrent inserts.
drop index if exists athena_metric.IDXadh6fulxe89os666w4l6aeepb;
alter table athena_metric.action
    add constraint uk_metric_action_identity unique (name, type, target, command);
