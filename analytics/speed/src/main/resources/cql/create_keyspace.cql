CREATE KEYSPACE IF NOT EXISTS iot_lambda_speed WITH REPLICATION = {
    'class' : 'SimpleStrategy',
    'replication_factor' : 1
};