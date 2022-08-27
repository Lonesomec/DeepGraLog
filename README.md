# DeepGraLog
A sort of Log Anomaly Detection method based on Graph Representation Learning.</br>
Original project address: https://github.com/Qbian61/forum-java.</br>
We have made the following modifications for this project:</br>

1. Modified the log format and eliminated redundant log information to facilitate data processing.</br>
2. Four types of defects are injected into system code.(see comments section)</br>
...
# Dataset
The dataset includes 5,076,117 log messages which are combined with invocation information of
the system source code files, and among the log messages,2010200 (39.6%) are anomalous ones caused by the four faults
injected in. </br>

You can get it from the release v1.0.</br>

## Paper Reference

Z. Chen, S. Kommrusch, M. Tufano, L.-N. Pouchet, D. Poshyvanyk,and M. Monperrus </br>
**Sequencer: Sequence-to-sequence learning for end-to-end program repair.**</br>
IEEE Transactions on Software Engineering </br>
vol. 47, no. 9, pp. 1943–1</br>
