import os
from .io import findJavaFiles

def test_findJavaFiles():
    # Create a temporary directory and some Java files for testing
    root_dir = 'test_find_java_files'
    os.mkdir(root_dir)

    sub_dir1 = os.path.join(root_dir, 'subdir1')
    os.mkdir(sub_dir1)
    with open(os.path.join(sub_dir1, 'test1.java'), 'w') as f:
        f.write('public class Test1 {}')

    sub_dir2 = os.path.join(root_dir, 'subdir2')
    os.mkdir(sub_dir2)
    with open(os.path.join(sub_dir2, 'test2.java'), 'w') as f:
        f.write('public class Test2 {}')

    # Test find_java_files()
    java_files = findJavaFiles(root_dir)
    assert len(java_files) == 2
    assert os.path.join(sub_dir1, 'test1.java') in java_files
    assert os.path.join(sub_dir2, 'test2.java') in java_files

    # Clean up the test directory and files
    os.remove(os.path.join(sub_dir1, 'test1.java'))
    os.remove(os.path.join(sub_dir2, 'test2.java'))
    os.rmdir(sub_dir1)
    os.rmdir(sub_dir2)
    os.rmdir(root_dir)