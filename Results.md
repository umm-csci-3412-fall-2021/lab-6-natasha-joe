# Experimental results

We compared two servers:

1. Our final server, which creates a new thread for each incoming connection, and
2. the server in `test/sampleBin`, which is single-threaded.

To test the performance of these servers, we created a number of connections,
each of which sent the `test/etc/words.txt` file to the server. Then, we
measured how long it took the server to finish handling all connections.

| # of connections | Threaded server time taken | Unthreaded server time taken |
| ---------------- | -------------------------- | ---------------------------- |
| 10               | 1 sec                      | 2 sec                        |
| 30               | 6 sec                      | 7 sec                        |
| 100              | 21 sec                     | 29 sec                       |
| 150              | 35 sec                     | 37 sec                       |

With these results, we can see that the threaded server is slightly faster than the
unthreaded server across a variety of load levels.
