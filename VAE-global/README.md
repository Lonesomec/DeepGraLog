# Variational Graph Auto-encoder in Pytorch
This repository implements variational graph auto-encoder by Thomas Kipf. For details of the model, 
refer to his original [tensorflow implementation](https://github.com/tkipf/gae) and [his paper](https://arxiv.org/abs/1611.07308).

# Requirements

* Pytorch 
* python 3.x
* networkx
* scikit-learn
* scipy

# How to run
* Specify your arguments in `args.py` : you can change dataset and other arguments there
* run `python train.py`

# Notes

* The dataset is the benchmark dataset research proposed.
* Per-epoch training time is a bit slower then the original implementation.(0.2 sec/epoch --> 0.9 sec/epoch)
* Train accuracy, validation(test) average precision, auroc are similar to those of the original. (over 90% for both AP and roc) 

