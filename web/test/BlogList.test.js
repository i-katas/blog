import React from 'react'
import BlogList from 'BlogList'
describe('BlogList', () => {
    it('display empty blogs', function () {
        let blogs = <BlogList items={[]}/>;

        expect(blogs).not.toBeUndefined()
    });
});