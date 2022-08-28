from graphsage_nodeclassify import *


dimensionality = 12
path_file = ''  # Other file paths
path_error = ''  # Address of the error type you want to test

# Other file training and test partition proportion
arg_input = input('1: training5 test5, 2: training all\n')
if arg_input == '1':
    divide = 0.5
elif arg_input == '2':
    divide = 1
else:
    print('Input error!')


#Load Dataset
list_file = []
dir_file = []

list_train=[] # the training set file address
list_test=[]  # the test set file address

get_file_path(path_file, list_file, dir_file)
list_file=shuffle(list_file)
for i in list_file[:int(len(list_file) * divide)]:
    list_train.append(i)
for j in list_file[int(len(list_file) * divide):]:
    list_test.append(j)

dir_test=[]
get_file_path(path_error, list_test,dir_test)


batch_size = 32
random.seed(824)
np.random.seed(824)
torch.manual_seed(824)
torch.cuda.manual_seed_all(824)

epochs = 10

device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
graphSage = GraphSage(1, dimensionality, 16, device, gcn='store_true', agg_func='MEAN')  # Create the GraphSage object

num_labels = 2

classification = Classification(16, num_labels) # Create the classificator
print('GraphSage with Supervised Learning')

#Training
for epoch in tqdm(range(epochs)):
    time.sleep(0.0001)
    print('----------------------EPOCH %d-----------------------' % epoch)
    i = 0
    loss_all = 0
    list_train = shuffle(list_train)
    for count in range(math.ceil(len(list_train) / batch_size)):
        if len(list_train[i:i + batch_size]) == batch_size:
            graphSage, classification, loss = apply_model(list_train[i:i + batch_size], graphSage,classification,1, device)
            loss_all += loss
        else:
            graphSage, classification, loss = apply_model(list_train[i:], graphSage, classification, 1, device)
            loss_all += loss
        i += batch_size
    print('loss:', loss_all/len(list_train))

# Verify model performance on the test set
labels_val_all=[]
predicts_val_all=[]
labels_test_all=[]
predicts_test_all=[]
num = 0
list_test=shuffle(list_test)
for count in tqdm(range(math.ceil(len(list_test) / batch_size))):
    time.sleep(0.0001)
    max_vali_f1 = 0
    if len(list_test[num:num + batch_size]) == batch_size:
        dc_test = DataCenter_test(list_test[num:num + batch_size])
        dc_test.load_Dataset()
        ds_test = 'test'
        labels_val,predicts_val,labels_test,predicts_test = evaluate(dc_test, ds_test, graphSage, classification, device,'debug', epoch)
    else:
        dc_test = DataCenter_test(list_test[num:])
        dc_test.load_Dataset()
        ds_test = 'test'
        labels_val,predicts_val,labels_test,predicts_test = evaluate(dc_test, ds_test, graphSage, classification, device,'debug', epoch)

    num += batch_size
    for val in labels_val:
        labels_val_all.append(val)
    for i in predicts_val:
        predicts_val_all.append(i)

    for test in labels_test:
        labels_test_all.append(test)
    for j in predicts_test:
        predicts_test_all.append(j)


#Calculate F1,Precision,recall
labels = np.array(labels_test_all)
scores = np.array(predicts_test_all)
TP = 0
FP = 0
FN = 0
TN = 0
for k in range(0, labels.shape[0]):
    if scores[k] ==0 and labels[k] == 0:
        TP += 1
    if scores[k] == 0 and labels[k] ==1:
        FP += 1
    if scores[k] == 1 and labels[k] == 0:
        FN += 1
    if scores[k] == 1 and labels[k] == 1:
        TN += 1

accuracy = (TP + TN) / (TP + FP + FN + TN)
precision = TP / (TP + FP)
recall = TP / (TP + FN)
F1 = 2 * precision * recall / (precision + recall)
print('test_f1_all',F1)
print('test_p_all',precision)
print('test_r_all', recall)