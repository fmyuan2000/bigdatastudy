

1. 找出全部夺得3连贯的队伍

```sql
select distinct team from (select team,year,rank() over (partition by team order by year) rank from t1) t2 where rank>3
```

2. 找出每个id在在一天之内所有的波峰与波谷值

```sql
select id,time,price, case when  price>before and after<price then '波峰'   when price < before and price <after then '波谷' else '' end as feature from (select id, time,price,lag(price) over(partition by id order by time) before,lead(price) over(partition by id order by time) after from t2) t3 where (price >before and after <price) or (price <before and price <after);

```

3.

```
select id , nvl(sub,0) sub,case when sub>30 then 1
                 when sub<30 then 0
                 else 0 end lianxu from (
select id,dt,dt2, (unix_timestamp(dt,'yyyy-MM-dd HH:mm')-unix_timestamp(dt2,'yyyy-MM-dd HH:mm'))/60 as sub  from (select id ,regexp_replace(dt, "/", "-") dt,regexp_replace(dt2, "/", "-") dt2 from (select id,dt,lag(dt) over(partition by id order by dt) dt2 from t3) t4)t5) t6;

```

